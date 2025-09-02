import 'package:flutter/material.dart';

class AppColors {
  static const primary = Colors.indigo;
  static const secondary = Colors.blueAccent;
  static const background = Color(0xFFF8F8F8);
  static const cardBackground = Colors.white;
  static const textPrimary = Colors.black87;
  static const textSecondary = Colors.black54;
}

class AppPadding {
  static const double small = 8.0;
  static const double medium = 16.0;
  static const double large = 24.0;
}

class AppTextStyle {
  static const TextStyle headline = TextStyle(
    fontSize: 20,
    fontWeight: FontWeight.bold,
    color: AppColors.textPrimary,
  );

  static const TextStyle subtitle = TextStyle(
    fontSize: 16,
    color: AppColors.textSecondary,
  );

  static const TextStyle button = TextStyle(
    fontSize: 16,
    fontWeight: FontWeight.bold,
    color: Colors.white,
  );
}
