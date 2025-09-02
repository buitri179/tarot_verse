import 'dart:convert';
import 'package:http/http.dart' as http;

class AuthService {
  static const String baseUrl = "http://localhost:8080/api/auth";

  Future<bool> login(String username, String password) async {
    final response = await http.post(
      Uri.parse("$baseUrl/login"),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'username': username, 'password': password}),
    );

    if (response.statusCode == 200) {
      final body = jsonDecode(response.body);
      // TODO: Lưu token vào flutter_secure_storage
      return true;
    }
    return false;
  }
}
