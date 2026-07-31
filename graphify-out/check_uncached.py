from pathlib import Path
from collections import Counter

lines = Path('graphify-out/.graphify_uncached.txt').read_text(encoding='utf-8').strip().split('\n')
lines = [l for l in lines if l.strip()]
print(f'Total uncached: {len(lines)}')
for l in lines[:10]:
    print(l)
print('...')
exts = Counter(Path(l).suffix.lower() for l in lines)
for ext, cnt in sorted(exts.items(), key=lambda x: -x[1]):
    print(f'  {repr(ext)}: {cnt}')
