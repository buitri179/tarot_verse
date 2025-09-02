// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

User _$UserFromJson(Map<String, dynamic> json) => User(
  id: (json['id'] as num).toInt(),
  name: json['name'] as String,
  birthDate: DateTime.parse(json['birthDate'] as String),
  gender: json['gender'] as String,
  facebookId: json['facebookId'] as String,
);

Map<String, dynamic> _$UserToJson(User instance) => <String, dynamic>{
  'id': instance.id,
  'name': instance.name,
  'birthDate': instance.birthDate.toIso8601String(),
  'gender': instance.gender,
  'facebookId': instance.facebookId,
};
