# 1️⃣ Chọn image Java 17
FROM eclipse-temurin:17-jdk-alpine

# 2️⃣ Thư mục làm việc trong container
WORKDIR /app

# 3️⃣ Copy toàn bộ project vào
COPY . .

# 4️⃣ Build project bằng Maven Wrapper
RUN ./mvnw clean package -DskipTests

# 5️⃣ Chạy file jar với port Render cấp
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar target/tarot_verse_backend.jar"]

# 6️⃣ Expose port (Render sẽ override bằng $PORT)
EXPOSE 8080
