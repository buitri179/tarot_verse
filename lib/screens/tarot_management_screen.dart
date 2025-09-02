import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../models/tarot_result.dart';
import '../providers/tarot_provider.dart';

class TarotManagementScreen extends StatefulWidget {
  const TarotManagementScreen({super.key});

  @override
  State<TarotManagementScreen> createState() => _TarotManagementScreenState();
}

class _TarotManagementScreenState extends State<TarotManagementScreen> {
  String searchQuery = '';
  int currentPage = 0;
  final int rowsPerPage = 5;

  @override
  void initState() {
    super.initState();
    // Load tarot results
    Provider.of<TarotProvider>(context, listen: false).loadResults();
  }

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<TarotProvider>(context);

    // Filter results
    List<TarotResult> filteredResults = provider.results
        .where((res) => res.userName.toLowerCase().contains(searchQuery.toLowerCase()))
        .toList();

    // Safe pagination
    int pageCount = (filteredResults.length / rowsPerPage).ceil();
    if (currentPage >= pageCount && pageCount > 0) {
      currentPage = pageCount - 1;
    }
    int start = currentPage * rowsPerPage;
    int end = start + rowsPerPage;
    if (end > filteredResults.length) end = filteredResults.length;
    List<TarotResult> pagedResults =
    filteredResults.isNotEmpty ? filteredResults.sublist(start, end) : [];

    return Padding(
      padding: const EdgeInsets.all(16),
      child: Column(
        children: [
          // Search bar
          TextField(
            decoration: InputDecoration(
              prefixIcon: const Icon(Icons.search),
              hintText: 'Search by user name...',
              border: OutlineInputBorder(borderRadius: BorderRadius.circular(8)),
            ),
            onChanged: (val) {
              setState(() {
                searchQuery = val;
                currentPage = 0; // reset page on search
              });
            },
          ),
          const SizedBox(height: 16),

          // Table
          provider.isLoading
              ? const Expanded(child: Center(child: CircularProgressIndicator()))
              : pagedResults.isEmpty
              ? const Expanded(child: Center(child: Text('No results found')))
              : Expanded(
            child: SingleChildScrollView(
              scrollDirection: Axis.horizontal,
              child: Container(
                decoration: BoxDecoration(
                  border: Border.all(color: Colors.grey.shade300),
                  borderRadius: BorderRadius.circular(8),
                ),
                child: Column(
                  children: [
                    // Header
                    Container(
                      decoration: BoxDecoration(
                        color: Colors.grey[100],
                        border: Border(bottom: BorderSide(color: Colors.grey.shade300)),
                      ),
                      child: Row(
                        children: const [
                          _TableHeader(title: 'ID', width: 80),
                          _TableHeader(title: 'User Name', width: 150),
                          _TableHeader(title: 'Card 1', width: 100),
                          _TableHeader(title: 'Detail 1', width: 200),
                          _TableHeader(title: 'Card 2', width: 100),
                          _TableHeader(title: 'Detail 2', width: 200),
                          _TableHeader(title: 'Card 3', width: 100),
                          _TableHeader(title: 'Detail 3', width: 200),
                          _TableHeader(title: 'Summary', width: 250),
                          _TableHeader(title: 'Actions', width: 80),
                        ],
                      ),
                    ),

                    // Data rows
                    ...pagedResults.map((res) {
                      return MouseRegion(
                        cursor: SystemMouseCursors.click,
                        child: Container(
                          decoration: BoxDecoration(
                            border: Border(
                              bottom: BorderSide(color: Colors.grey.shade200),
                            ),
                          ),
                          child: Row(
                            children: [
                              _TableCell(text: '#${res.id}', width: 80),
                              _TableCell(text: res.userName, width: 150),
                              _TableCell(
                                  text: res.cards.isNotEmpty ? res.cards[0] : '',
                                  width: 100),
                              _TableCell(text: res.detailCard1, width: 200),
                              _TableCell(
                                  text: res.cards.length > 1 ? res.cards[1] : '',
                                  width: 100),
                              _TableCell(text: res.detailCard2, width: 200),
                              _TableCell(
                                  text: res.cards.length > 2 ? res.cards[2] : '',
                                  width: 100),
                              _TableCell(text: res.detailCard3, width: 200),
                              _TableCell(text: res.summary, width: 250),
                              _TableCell(
                                width: 80,
                                child: IconButton(
                                  icon: const Icon(Icons.delete, color: Colors.red),
                                  onPressed: () async {
                                    await provider.deleteResult(res.id);
                                    ScaffoldMessenger.of(context).showSnackBar(
                                      const SnackBar(content: Text('Result deleted')),
                                    );
                                  },
                                ),
                              ),
                            ],
                          ),
                        ),
                      );
                    }).toList(),
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
                  onPressed: currentPage > 0 ? () => setState(() => currentPage--) : null,
                ),
                Text('Page ${currentPage + 1} of $pageCount'),
                IconButton(
                  icon: const Icon(Icons.chevron_right),
                  onPressed: currentPage < pageCount - 1 ? () => setState(() => currentPage++) : null,
                ),
              ],
            ),
        ],
      ),
    );
  }
}

// Table header widget
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
        style: const TextStyle(fontWeight: FontWeight.bold, color: Colors.grey, fontSize: 12),
      ),
    );
  }
}

// Table cell widget
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
