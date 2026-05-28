run:
    docker compose up -d
    mvn spring-boot:run &
    cd frontend && npm run dev
