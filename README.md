# nat1-website-scraping

Scrape blog data from [nat1-website](https://nat1-website.agungdh.my.id) using Playwright and output a JSON file.

## Docker

```bash
# Pull image
docker pull agungdh/nat1-website-scraping

# Run (output blog-data-YYYYMMDD-HHmmss.json ke folder ./output)
docker run --rm -v $(pwd)/output:/output -e OUTPUT_DIR=/output agungdh/nat1-website-scraping
```

Setelah selesai, file `output/blog-data-YYYYMMDD-HHmmss.json` akan berisi data:
- `categories` — semua kategori unik
- `tags` — semua tag unik
- `posts` — array blog post (title, excerpt, date, category, tags, url)

## Run from source

```bash
# Install dependencies & browser
./gradlew build -x test
./gradlew installPlaywrightBrowsers

# Run (non-headless, browser terlihat)
HEADLESS=false ./gradlew bootRun

# Atau headless
./gradlew bootRun
```
