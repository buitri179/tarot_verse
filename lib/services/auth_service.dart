import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class AuthService {
  static const String baseUrl = "http://localhost:8080/api/auth";
  final _storage = const FlutterSecureStorage();

  /// Login, lưu token nếu thành công
  Future<bool> login(String username, String password) async {
    try {
      final response = await http.post(
        Uri.parse("$baseUrl/login"),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'username': username, 'password': password}),
      );

      if (response.statusCode == 200) {
        final body = jsonDecode(response.body);
        final token = body['token'];
        if (token != null && token.isNotEmpty) {
          await _storage.write(key: 'jwt_token', value: token);
          return true;
        }
      } else {
        // Có thể đọc lỗi trả về từ backend
        final body = jsonDecode(response.body);
        print("Login failed: ${body['error'] ?? 'Unknown error'}");
      }
    } catch (e) {
      print("Exception during login: $e");
    }

    return false;
  }

  /// Lấy token
  Future<String?> getToken() async {
    try {
      return await _storage.read(key: 'jwt_token');
    } catch (e) {
      print("Exception reading token: $e");
      return null;
    }
  }

  /// Logout
  Future<void> logout() async {
    try {
      await _storage.delete(key: 'jwt_token');
    } catch (e) {
      print("Exception during logout: $e");
    }
  }

  /// Kiểm tra đã login chưa
  Future<bool> isLoggedIn() async {
    final token = await getToken();
    return token != null && token.isNotEmpty;
  }
}
