import pymupdf
import sys
import os

# Create a test PDF
def create_test_pdf(filename):
    doc = pymupdf.open()
    page = doc.new_page()
    page.insert_text((72, 72), "This is a test document for the PodcastPDF application. It contains readable digital text.")
    doc.save(filename)
    doc.close()
    print(f"Created {filename}")

# Try to parse it
def test_parse(filename):
    text_parts = []
    with pymupdf.open(filename) as doc:
        print(f"PDF Info: {doc.page_count} pages")
        for page in doc:
            text_parts.append(page.get_text())
    
    full_text = "\n".join(text_parts)
    print(f"Extracted {len(full_text)} characters.")
    if len(full_text) > 0:
        print("SUCCESS: Text extracted correctly.")
    else:
        print("FAILURE: No text extracted.")

if __name__ == "__main__":
    fname = "verify_test.pdf"
    create_test_pdf(fname)
    test_parse(fname)
    if os.path.exists(fname):
        os.remove(fname)
