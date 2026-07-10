FROM debian:bookworm-slim

RUN apt-get update && apt-get install -y --no-install-recommends \
    wget ca-certificates unzip gnupg \
    libxcb-shm0 libx11-xcb1 libnss3 libnspr4 libdbus-1-3 libatk1.0-0 \
    libatk-bridge2.0-0 libcups2 libdrm2 libxkbcommon0 libxcomposite1 \
    libxdamage1 libxfixes3 libxrandr2 libgbm1 libpango-1.0-0 libcairo2 \
    libasound2 libatspi2.0-0 \
    && rm -rf /var/lib/apt/lists/*

RUN wget -qO- https://packages.adoptium.net/artifactory/api/gpg/key/public | tee /etc/apt/keyrings/adoptium.asc \
    && echo "deb [signed-by=/etc/apt/keyrings/adoptium.asc] https://packages.adoptium.net/artifactory/deb bookworm main" > /etc/apt/sources.list.d/adoptium.list \
    && apt-get update && apt-get install -y temurin-25-jdk \
    && rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/temurin-25-jdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH

COPY build/libs/test-scraping-0.0.1-SNAPSHOT.jar /app/app.jar

ENV PLAYWRIGHT_BROWSERS_PATH=/ms-playwright

RUN cd /tmp && jar xf /app/app.jar BOOT-INF/lib/playwright-1.60.0.jar BOOT-INF/lib/driver-1.60.0.jar BOOT-INF/lib/driver-bundle-1.60.0.jar && \
    java -cp "BOOT-INF/lib/*" com.microsoft.playwright.CLI install chromium && \
    rm -rf /tmp/BOOT-INF

WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
