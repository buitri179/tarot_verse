import 'package:flutter/material.dart';
import '../models/tarot_result.dart';
import '../services/api_service.dart';

class TarotProvider extends ChangeNotifier {
  List<TarotResult> _results = [];
  bool _isLoading = false;

  List<TarotResult> get results => _results;
  bool get isLoading => _isLoading;

  Future<void> loadResults() async {
    _isLoading = true;
    notifyListeners();

    try {
      _results = await ApiService().fetchTarotResults();
    } catch (e) {
      debugPrint("Error loading tarot results: $e");
    }

    _isLoading = false;
    notifyListeners();
  }

  Future<void> deleteResult(int id) async {
    try {
      await ApiService().deleteTarotResult(id); // thêm method trong ApiService
      _results.removeWhere((r) => r.id == id);
      notifyListeners();
    } catch (e) {
      debugPrint("Error deleting tarot result: $e");
    }
  }
}
