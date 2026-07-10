FROM eclipse-temurin:25-jdk

RUN apt-get update && apt-get install -y --no-install-recommends \
    libicu66 libjpeg-turbo8 libwebp6 libffi7 \
    && rm -rf /var/lib/apt/lists/*

COPY build/libs/test-scraping-0.0.1-SNAPSHOT.jar /app/app.jar

ENV PLAYWRIGHT_BROWSERS_PATH=/ms-playwright

RUN java -cp /app/app.jar -Dplaywright.cli.dir=$PLAYWRIGHT_BROWSERS_PATH com.microsoft.playwright.CLI install chromium

WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
