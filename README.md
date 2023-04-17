# challenge-platform-backend

- [x] register -> login (crypted password)

- [x] admin - controller за създаване/ променяне/ триене на роли.
- [x] AccountsRolesRepository vs RoleRepository - каква е разликата, едното да се премахне

AccountsRolesRepository - за таблица accounts_roles
RoleRepository - за таблица roles

--- 
- [x] challenge(user) - create, edit, delete, get
- [x] badge (moderator/admin) - get, create, edit, delete
- [x] account_challenge (user/moderator/admin) - get, create(start), edit(finish), delete
- [X] когато се завърши предизвикателство трябва да се запише в таблицата Account_Completed_Challenges
- [X] когато се завърши предизвикателство, човекът автоматично получава badge 

---
- [x] Frontend- login/create user, бутон за напускане на страницата (log out)
- [x] страница с изброени всички предизвикателства
- [x] като кликне на предизвикателство да се отварят подробности, бутон за стартиране на предизвикателството
- [x] страница със започнатите предизвикателства, бутон за завършване на предизвикателството, бутон за отказване от предизвикателството
- [x] страница с баджовете на потребителя
- [x] страница с всички потребители, при натискане на потребител да се отваря нова страница с неговата информация
- [x] страница с информация за създателя

to create image:
create jar -> ./mvnw package
create image -> docker build -t challenge-platform-backend .