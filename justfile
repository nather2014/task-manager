dev:
    podman compose up -d
    cd backend && mvn spring-boot:run &
    cd frontend && npm run dev