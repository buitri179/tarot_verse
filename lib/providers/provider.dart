import 'package:provider/provider.dart';
import 'package:provider/single_child_widget.dart';
import 'user_provider.dart';
import 'tarot_provider.dart';

class AppProviders {
  static List<SingleChildWidget> providers = [
    ChangeNotifierProvider(create: (_) => UserProvider()),
    ChangeNotifierProvider(create: (_) => TarotProvider()),
  ];
}
