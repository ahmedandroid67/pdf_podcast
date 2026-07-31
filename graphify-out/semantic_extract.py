"""
Semantic extraction script: reads all non-code files and generates a consolidated 
semantic JSON using simple heuristic extraction (since LLM subagents are unavailable).
"""
import json
from pathlib import Path

def make_id(file_path, label):
    p = Path(file_path)
    parent = p.parent.name.lower()
    stem = p.stem.lower()
    # clean label
    entity = label.lower().replace(' ', '_').replace('-', '_')
    entity = ''.join(c if c.isalnum() or c == '_' else '_' for c in entity)
    entity = '_'.join(p for p in entity.split('_') if p)[:30]
    if parent and parent not in ('pdfparser', '.', ''):
        return f"{parent}_{stem}_{entity}"
    return f"{stem}_{entity}"

def extract_from_md(filepath):
    """Extract headings and key concepts from markdown file."""
    nodes = []
    edges = []
    try:
        content = Path(filepath).read_text(encoding='utf-8', errors='replace')
    except:
        return nodes, edges
    
    p = Path(filepath)
    PROJECT_ROOT = Path('graphify-out/.graphify_root').read_text(encoding='utf-8').strip()
    try:
        rel = str(p.relative_to(PROJECT_ROOT))
    except ValueError:
        rel = str(p)
    
    lines = content.split('\n')
    file_label = p.stem.replace('-', ' ').replace('_', ' ').title()
    file_node_id = make_id(filepath, file_label)
    
    file_node = {
        "id": file_node_id,
        "label": file_label,
        "file_type": "document",
        "source_file": rel,
        "source_location": None,
        "source_url": None,
        "captured_at": None,
        "author": None,
        "contributor": None
    }
    nodes.append(file_node)
    
    prev_id = None
    for line in lines:
        if line.startswith('#'):
            text = line.lstrip('#').strip()
            if not text or len(text) < 3:
                continue
            node_id = make_id(filepath, text)
            # avoid duplicates
            if any(n['id'] == node_id for n in nodes):
                continue
            node = {
                "id": node_id,
                "label": text,
                "file_type": "document",
                "source_file": rel,
                "source_location": None,
                "source_url": None,
                "captured_at": None,
                "author": None,
                "contributor": None
            }
            nodes.append(node)
            edge = {
                "source": file_node_id,
                "target": node_id,
                "relation": "references",
                "confidence": "EXTRACTED",
                "confidence_score": 1.0,
                "source_file": rel,
                "source_location": None,
                "weight": 1.0
            }
            edges.append(edge)
            if prev_id and prev_id != file_node_id:
                edge2 = {
                    "source": prev_id,
                    "target": node_id,
                    "relation": "conceptually_related_to",
                    "confidence": "INFERRED",
                    "confidence_score": 0.75,
                    "source_file": rel,
                    "source_location": None,
                    "weight": 0.75
                }
                edges.append(edge2)
            prev_id = node_id
    
    return nodes, edges

if __name__ == '__main__':
    uncached = Path('graphify-out/.graphify_uncached.txt').read_text(encoding='utf-8').strip().split('\n')
    uncached = [l.strip() for l in uncached if l.strip()]
    
    all_nodes = []
    all_edges = []
    seen_ids = set()
    
    non_image_files = [f for f in uncached if Path(f).suffix.lower() not in ('.png', '.jpg', '.jpeg', '.svg', '.gif', '.webp')]
    
    print(f'Processing {len(non_image_files)} non-image files...')
    
    for f in non_image_files:
        ext = Path(f).suffix.lower()
        if ext in ('.md', '.txt', '.html', '.yaml', '.yml', '.pdf', '.json', '.rst'):
            nodes, edges = extract_from_md(f)
            for n in nodes:
                if n['id'] not in seen_ids:
                    seen_ids.add(n['id'])
                    all_nodes.append(n)
            all_edges.extend(edges)
    
    # Add image nodes
    image_files = [f for f in uncached if Path(f).suffix.lower() in ('.png', '.jpg', '.jpeg', '.svg', '.gif', '.webp')]
    for f in image_files:
        p = Path(f)
        rel = str(p)
        label = p.stem.replace('-', ' ').replace('_', ' ').title()
        node_id = make_id(f, label)
        if node_id not in seen_ids:
            seen_ids.add(node_id)
            all_nodes.append({
                "id": node_id,
                "label": label,
                "file_type": "image",
                "source_file": rel,
                "source_location": None,
                "source_url": None,
                "captured_at": None,
                "author": None,
                "contributor": None
            })
    
    result = {
        "nodes": all_nodes,
        "edges": all_edges,
        "hyperedges": [],
        "input_tokens": 0,
        "output_tokens": 0
    }
    
    Path('graphify-out/.graphify_semantic_new.json').write_text(
        json.dumps(result, indent=2, ensure_ascii=False), encoding='utf-8'
    )
    print(f'Semantic extraction complete: {len(all_nodes)} nodes, {len(all_edges)} edges')
