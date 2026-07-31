import json
from graphify.cache import check_semantic_cache
from pathlib import Path

detect = json.loads(Path('graphify-out/.graphify_detect.json').read_text(encoding='utf-8-sig'))

# Collect all non-code, non-video files
all_files = [f for cat, files in detect['files'].items() for f in files if cat not in ('video', 'code')]

# Add unique transcripts
transcript_paths_raw = json.loads(Path('graphify-out/.graphify_transcripts_clean.json').read_text(encoding='utf-8-sig')) if Path('graphify-out/.graphify_transcripts_clean.json').exists() else []
unique_transcripts = list(dict.fromkeys(transcript_paths_raw))
all_files.extend(unique_transcripts)

cached_nodes, cached_edges, cached_hyperedges, uncached = check_semantic_cache(all_files)

if cached_nodes or cached_edges or cached_hyperedges:
    Path('graphify-out/.graphify_cached.json').write_text(json.dumps({'nodes': cached_nodes, 'edges': cached_edges, 'hyperedges': cached_hyperedges}, ensure_ascii=False), encoding='utf-8')
else:
    Path('graphify-out/.graphify_cached.json').write_text(json.dumps({'nodes': [], 'edges': [], 'hyperedges': []}), encoding='utf-8')

Path('graphify-out/.graphify_uncached.txt').write_text('\n'.join(uncached), encoding='utf-8')
print(f'Total non-code files: {len(all_files)}')
print(f'Cache: {len(all_files)-len(uncached)} files hit, {len(uncached)} files need extraction')
