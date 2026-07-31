from pathlib import Path
import json

lines = Path('graphify-out/.graphify_uncached.txt').read_text(encoding='utf-8').strip().split('\n')
lines = [l.strip() for l in lines if l.strip()]

images = [l for l in lines if Path(l).suffix.lower() in ('.png', '.jpg', '.jpeg', '.svg', '.gif', '.webp')]
docs = [l for l in lines if l not in images]

print(f'Images: {len(images)}')
print(f'Docs: {len(docs)}')

# Write chunks
chunk_size = 22
chunks = []

# Docs in chunks of 22
for i in range(0, len(docs), chunk_size):
    chunks.append(docs[i:i+chunk_size])

# Each image in its own chunk
for img in images:
    chunks.append([img])

print(f'Total chunks: {len(chunks)}')
for i, chunk in enumerate(chunks):
    print(f'Chunk {i+1}: {len(chunk)} files ({Path(chunk[0]).suffix} ... {Path(chunk[-1]).suffix})')
    chunk_data = '\n'.join(chunk)
    Path(f'graphify-out/chunk_plan_{i+1:02d}.txt').write_text(chunk_data, encoding='utf-8')
