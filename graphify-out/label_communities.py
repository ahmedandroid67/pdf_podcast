import sys, json
from graphify.build import build_from_json
from graphify.cluster import score_all
from graphify.analyze import god_nodes, surprising_connections, suggest_questions
from graphify.report import generate
from pathlib import Path

extraction = json.loads(Path('graphify-out/.graphify_extract.json').read_text(encoding='utf-8'))
detection  = json.loads(Path('graphify-out/.graphify_detect.json').read_text(encoding='utf-8-sig'))
analysis   = json.loads(Path('graphify-out/.graphify_analysis.json').read_text(encoding='utf-8'))

G = build_from_json(extraction)
communities = {int(k): v for k, v in analysis['communities'].items()}
cohesion = {int(k): v for k, v in analysis['cohesion'].items()}
tokens = {'input': extraction.get('input_tokens', 0), 'output': extraction.get('output_tokens', 0)}

# Community labels based on node content inspection
labels = {
    0: "Arabic Localization",
    1: "English Localization",
    2: "French Localization",
    3: "Perl Testing Patterns",
    4: "Autonomous Loop Skills",
    5: "Django Security",
    6: "Django Verification",
    7: "API Design Patterns",
    8: "Perl Code Patterns",
    9: "VideoDB Real-Time Streaming",
    10: "Docker Security Patterns",
    11: "Kotlin Testing",
    12: "Frontend PWA Service Worker",
}

# Fill remaining communities with generic labels
for cid in sorted(communities.keys()):
    if cid not in labels:
        members = communities[cid][:3]
        # derive label from node IDs
        sample = members[0] if members else f'community_{cid}'
        parts = sample.split('_')
        if len(parts) > 1:
            domain = parts[0].replace('-', ' ').title()
            labels[cid] = f"{domain} Module"
        else:
            labels[cid] = f"Community {cid}"

questions = suggest_questions(G, communities, labels)

report = generate(G, communities, cohesion, labels, analysis['gods'], analysis['surprises'], detection, tokens, '.', suggested_questions=questions)
Path('graphify-out/GRAPH_REPORT.md').write_text(report, encoding='utf-8')
Path('graphify-out/.graphify_labels.json').write_text(json.dumps({str(k): v for k, v in labels.items()}, ensure_ascii=False), encoding='utf-8')
print('Report updated with community labels')
print(f'Top suggested questions:')
for q in questions[:3]:
    print(f'  - {q}')
