# LedgerX – Monorepo (Java 21 / Spring Boot 3.5.x)

“LedgerX” – une plateforme de gestion de lignes de crédit (crédits, décisions, refinancement, événements & audit), exposée en microservices.
- **identity-service** : AuthN/AuthZ via **Keycloak** (Resource Server JWT)
- **credit-service** : JPA / **Specification** / **Criteria** 
- **reporting-service** : projections CQRS & exports 
- **gateway** : API Gateway 

---

## ⚙️ Prérequis
- **JDK 21**
- **Maven 3.9+**
- **Docker** / Docker Compose
- (Optionnel) `curl`, `jq` pour tester les tokens

---

## 🗂️ Arborescence
```text
ledgerx/
├── services/
│   ├── identity-service/
│   ├── credit-service/
│   ├── reporting-service/
│   └── gateway/
├── docker-compose.yml
└── pom.xml   # parent (aggregator + BOM)
