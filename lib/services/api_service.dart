import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/user.dart';
import '../models/tarot_result.dart';
import '../models/login_request.dart';
import '../models/login_response.dart';

class ApiService {
  static const String baseUrl = "http://localhost:8080/api";

  /// Lấy danh sách users
  Future<List<User>> fetchUsers() async {
    final response = await http.get(Uri.parse("$baseUrl/users"));
    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((e) => User.fromJson(e)).toList();
    } else {
      throw Exception("Failed to load users");
    }
  }

  /// Xóa user theo ID
  Future<bool> deleteUser(int id) async {
    final response = await http.delete(Uri.parse("$baseUrl/users/$id"));
    return response.statusCode == 200;
  }

  /// Lấy danh sách tarot results
  Future<List<TarotResult>> fetchTarotResults() async {
    final response = await http.get(Uri.parse("$baseUrl/tarot-results"));
    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body);
      return data.map((e) => TarotResult.fromJson(e)).toList();
    } else {
      throw Exception("Failed to load tarot results");
    }
  }

  /// Xóa tarot result theo ID
  Future<bool> deleteTarotResult(int id) async {
    final response = await http.delete(Uri.parse("$baseUrl/tarot-results/$id"));
    return response.statusCode == 200;
  }

  Future<LoginResponse> loginAdmin(LoginRequest request) async {
    final response = await http.post(
      Uri.parse('$baseUrl/admin/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(request.toJson()),
    );

    if (response.statusCode == 200) {
      return LoginResponse.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Login failed: ${response.body}');
    }
  }
}
