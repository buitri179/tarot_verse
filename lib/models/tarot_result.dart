import 'package:json_annotation/json_annotation.dart';

part 'tarot_result.g.dart';

@JsonSerializable()
class TarotResult {
  final int id;
  final String userName;
  final List<String> cards; // 3 lá bài
  final String detailCard1;
  final String detailCard2;
  final String detailCard3;
  final String summary;

  TarotResult({
    required this.id,
    required this.userName,
    required this.cards,
    required this.detailCard1,
    required this.detailCard2,
    required this.detailCard3,
    required this.summary,
  });

  factory TarotResult.fromJson(Map<String, dynamic> json) =>
      _$TarotResultFromJson(json);
  Map<String, dynamic> toJson() => _$TarotResultToJson(this);
}
