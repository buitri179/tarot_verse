# 1️⃣ Chọn image Java 17 Alpine nhẹ
FROM eclipse-temurin:17-jdk-alpine

# 2️⃣ Thiết lập thư mục làm việc
WORKDIR /app

# 3️⃣ Copy toàn bộ project vào container
COPY . .

# 4️⃣ Đảm bảo Maven Wrapper có quyền chạy (fix lỗi Windows -> Linux)
RUN chmod +x ./mvnw

# 5️⃣ Build project, bỏ qua tests
RUN ./mvnw clean package -DskipTests

# 6️⃣ Chạy ứng dụng Spring Boot với port Render cấp
#    $PORT là biến môi trường Render tự set
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar target/tarot_verse_backend.jar"]

# 7️⃣ Expose port mặc định (Render sẽ override bằng $PORT)
EXPOSE 8080
