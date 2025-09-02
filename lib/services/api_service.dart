import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/user.dart';
import '../models/tarot_result.dart';

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
}
