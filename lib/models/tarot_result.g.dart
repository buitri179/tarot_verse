// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'tarot_result.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

TarotResult _$TarotResultFromJson(Map<String, dynamic> json) => TarotResult(
  id: (json['id'] as num).toInt(),
  userName: json['userName'] as String,
  cards: (json['cards'] as List<dynamic>).map((e) => e as String).toList(),
  detailCard1: json['detailCard1'] as String,
  detailCard2: json['detailCard2'] as String,
  detailCard3: json['detailCard3'] as String,
  summary: json['summary'] as String,
);

Map<String, dynamic> _$TarotResultToJson(TarotResult instance) =>
    <String, dynamic>{
      'id': instance.id,
      'userName': instance.userName,
      'cards': instance.cards,
      'detailCard1': instance.detailCard1,
      'detailCard2': instance.detailCard2,
      'detailCard3': instance.detailCard3,
      'summary': instance.summary,
    };
