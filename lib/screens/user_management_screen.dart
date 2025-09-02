import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:intl/intl.dart';
import '../models/user.dart';
import '../providers/user_provider.dart';

class UserManagementScreen extends StatefulWidget {
  const UserManagementScreen({super.key});

  @override
  State<UserManagementScreen> createState() => _UserManagementScreenState();
}

class _UserManagementScreenState extends State<UserManagementScreen> {
  String searchQuery = '';
  int currentPage = 0;
  final int rowsPerPage = 5;

  @override
  void initState() {
    super.initState();
    Provider.of<UserProvider>(context, listen: false).loadUsers();
  }

  @override
  Widget build(BuildContext context) {
    final userProvider = Provider.of<UserProvider>(context);
    List<User> filteredUsers = userProvider.users
        .where((user) =>
        user.name.toLowerCase().contains(searchQuery.toLowerCase()))
        .toList();

    int pageCount = (filteredUsers.length / rowsPerPage).ceil();
    int start = currentPage * rowsPerPage;
    int end = start + rowsPerPage;
    if (end > filteredUsers.length) end = filteredUsers.length;
    List<User> pagedUsers = filteredUsers.sublist(start, end);

    return Padding(
      padding: const EdgeInsets.all(16),
      child: Column(
        children: [
          // Search bar
          Row(
            children: [
              Expanded(
                child: TextField(
                  decoration: InputDecoration(
                    prefixIcon: const Icon(Icons.search),
                    hintText: 'Search...',
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(8),
                    ),
                  ),
                  onChanged: (val) {
                    setState(() {
                      searchQuery = val;
                      currentPage = 0;
                    });
                  },
                ),
              ),
              const SizedBox(width: 16),
            ],
          ),
          const SizedBox(height: 16),
          // Table
          userProvider.isLoading
              ? const Expanded(
              child: Center(child: CircularProgressIndicator()))
              : pagedUsers.isEmpty
              ? const Expanded(
              child: Center(child: Text('No users found')))
              : Expanded(
            child: SingleChildScrollView(
              scrollDirection: Axis.horizontal,
              child: Container(
                decoration: BoxDecoration(
                  border: Border.all(color: Colors.grey.shade300),
                  borderRadius: BorderRadius.circular(8),
                  boxShadow: [
                    BoxShadow(
                      color: Colors.grey.withOpacity(0.1),
                      blurRadius: 4,
                      offset: const Offset(0, 2),
                    ),
                  ],
                ),
                child: Column(
                  children: [
                    // Header row
                    Container(
                      decoration: BoxDecoration(
                        color: Colors.grey[100],
                        border: Border(
                          bottom:
                          BorderSide(color: Colors.grey.shade300),
                        ),
                      ),
                      child: Row(
                        children: const [
                          _TableHeader(title: 'ID', width: 80),
                          _TableHeader(title: 'Name', width: 200),
                          _TableHeader(title: 'Date of Birth', width: 140),
                          _TableHeader(title: 'Gender', width: 100),
                          _TableHeader(title: 'Facebook ID', width: 180),
                          _TableHeader(title: 'Actions', width: 120),
                        ],
                      ),
                    ),
                    // Data rows
                    ...pagedUsers.map(
                          (user) => MouseRegion(
                        cursor: SystemMouseCursors.click,
                        child: Container(
                          decoration: BoxDecoration(
                            border: Border(
                              bottom: BorderSide(
                                  color: Colors.grey.shade200),
                            ),
                          ),
                          child: Row(
                            children: [
                              _TableCell(
                                  text: '#${user.id}', width: 80),
                              _TableCell(text: user.name, width: 200),
                              _TableCell(
                                  text: DateFormat('dd/MM/yyyy')
                                      .format(user.birthDate),
                                  width: 140),
                              _TableCell(
                                child: Container(
                                  padding: const EdgeInsets.symmetric(
                                      horizontal: 8, vertical: 4),
                                  decoration: BoxDecoration(
                                    color: user.gender == 'Male'
                                        ? Colors.blue[100]
                                        : user.gender == 'Female'
                                        ? Colors.pink[100]
                                        : Colors.green[100],
                                    borderRadius:
                                    BorderRadius.circular(12),
                                  ),
                                  child: Text(
                                    user.gender,
                                    style: TextStyle(
                                      color: user.gender == 'Male'
                                          ? Colors.blue[800]
                                          : user.gender == 'Female'
                                          ? Colors.pink[800]
                                          : Colors.green[800],
                                      fontWeight: FontWeight.bold,
                                    ),
                                  ),
                                ),
                                width: 100,
                              ),
                              _TableCell(
                                child: InkWell(
                                  onTap: () {
                                    // TODO: mở facebook
                                  },
                                  child: Text(
                                    user.facebookId,
                                    style: const TextStyle(
                                      color: Colors.blue,
                                      decoration:
                                      TextDecoration.underline,
                                    ),
                                  ),
                                ),
                                width: 180,
                              ),
                              _TableCell(
                                child: Row(
                                  children: [
                                    IconButton(
                                      icon: const Icon(Icons.delete,
                                          color: Colors.red),
                                      onPressed: () async {
                                        await userProvider
                                            .deleteUser(user.id);
                                        ScaffoldMessenger.of(context)
                                            .showSnackBar(
                                            const SnackBar(
                                                content: Text(
                                                    'User deleted')));
                                      },
                                    ),
                                  ],
                                ),
                                width: 120,
                              ),
                            ],
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),
          const SizedBox(height: 16),
          // Pagination
          if (pageCount > 1)
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                IconButton(
                  icon: const Icon(Icons.chevron_left),
                  onPressed: currentPage > 0
                      ? () {
                    setState(() {
                      currentPage--;
                    });
                  }
                      : null,
                ),
                Text('Page ${currentPage + 1} of $pageCount'),
                IconButton(
                  icon: const Icon(Icons.chevron_right),
                  onPressed: currentPage < pageCount - 1
                      ? () {
                    setState(() {
                      currentPage++;
                    });
                  }
                      : null,
                ),
              ],
            ),
        ],
      ),
    );
  }
}

class _TableHeader extends StatelessWidget {
  final String title;
  final double width;

  const _TableHeader({required this.title, required this.width});

  @override
  Widget build(BuildContext context) {
    return Container(
      width: width,
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 16),
      child: Text(
        title,
        style: const TextStyle(
            fontWeight: FontWeight.bold, color: Colors.grey, fontSize: 12),
      ),
    );
  }
}

class _TableCell extends StatelessWidget {
  final String? text;
  final Widget? child;
  final double width;

  const _TableCell({this.text, this.child, required this.width});

  @override
  Widget build(BuildContext context) {
    return Container(
      width: width,
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 16),
      child: child ?? Text(text ?? ''),
    );
  }
}
