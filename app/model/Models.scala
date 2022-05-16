package model

case class AuthInfo(access_token: String,
                        instance_url: String,
                        id: String,
                        token_type: String,
                        issued_at: String,
                        signature: String)

case class AuthResponse2(access_token: String,
                        instance_url: String,
                        id: String,
                        token_type: String,
                        issued_at: String,
                        signature: String)
