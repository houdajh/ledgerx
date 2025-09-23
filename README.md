# LedgerX – Monorepo (Java 21 / Spring Boot 3.5.x)

Plateforme pédagogique orientée **apprendre par la pratique** pour se préparer aux entretiens avancés Java/Spring :
- **identity-service** : AuthN/AuthZ via **Keycloak** (Resource Server JWT)
- **credit-service** : JPA / **Specification** / **Criteria** *(à venir S2)*
- **reporting-service** : projections CQRS & exports *(à venir)*
- **gateway** : API Gateway *(à venir)*

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
