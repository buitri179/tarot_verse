import 'package:flutter/material.dart';
import '../models/user.dart';
import '../services/api_service.dart';

class UserProvider extends ChangeNotifier {
  List<User> _users = [];
  bool _isLoading = false;

  List<User> get users => _users;
  bool get isLoading => _isLoading;

  /// Load tất cả user
  Future<void> loadUsers() async {
    _isLoading = true;
    notifyListeners();

    try {
      _users = await ApiService().fetchUsers();
    } catch (e) {
      debugPrint("Error loading users: $e");
    }

    _isLoading = false;
    notifyListeners();
  }

  /// Xóa user theo ID và cập nhật danh sách
  Future<void> deleteUser(int id) async {
    try {
      final success = await ApiService().deleteUser(id);
      if (success) {
        _users.removeWhere((user) => user.id == id);
        notifyListeners();
      } else {
        debugPrint("Failed to delete user with id $id");
      }
    } catch (e) {
      debugPrint("Error deleting user: $e");
    }
  }
}
