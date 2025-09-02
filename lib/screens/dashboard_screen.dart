import 'package:flutter/material.dart';
import '../widgets/custom_drawer.dart';
import 'user_management_screen.dart';
import 'tarot_management_screen.dart';

class DashboardScreen extends StatefulWidget {
  const DashboardScreen({super.key});

  @override
  State<DashboardScreen> createState() => _DashboardScreenState();
}

class _DashboardScreenState extends State<DashboardScreen> {
  int _selectedIndex = 0;

  final List<Widget> _pages = const [
    UserManagementScreen(),
    TarotManagementScreen(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Admin Dashboard")),
      drawer: const CustomDrawer(),
      body: _pages[_selectedIndex],
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: _selectedIndex,
        onTap: (i) => setState(() => _selectedIndex = i),
        items: const [
          BottomNavigationBarItem(
              icon: Icon(Icons.people), label: "Quản lý người dùng"),
          BottomNavigationBarItem(
              icon: Icon(Icons.style), label: "Kết quả bói Tarot"),
        ],
      ),
    );
  }
}
