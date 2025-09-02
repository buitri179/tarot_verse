import 'package:json_annotation/json_annotation.dart';

part 'user.g.dart';

@JsonSerializable()
class User {
  final int id;
  final String name;
  final DateTime birthDate;
  final String gender;
  final String facebookId;

  User({
    required this.id,
    required this.name,
    required this.birthDate,
    required this.gender,
    required this.facebookId,
  });

  factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);
  Map<String, dynamic> toJson() => _$UserToJson(this);
}
