# user-spring-security

```
[
	{
		"code": "DEAL",
		"privilege": ["READ"]
	},
	{
		"code": "DEAL/GLE",
		"privilege": ["WRITE"]
	}
]
```

```
DEAL_READ
DEAL/GLE_WRITE
```

http://localhost:8081/api/v1/rights/isGranted?user=steve.jobs&application=APPLE&perimeter=DEAL&privilege=READ
http://localhost:8081/api/v1/credentials?user=steve.jobs&password=5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8

http://localhost:8080/api/v1/test-secured