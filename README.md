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
- [] Frontend- login/create user, 
- [] страница с изброени всички предизвикателства
- [ ] като кликне на предизвикателство да се отварят подробности, бутон за стартиране на предизвикателството
- [ ] страница със започнатите предизвикателства, бутон за завършване на предизвикателството, бутон за отказване от предизвикателството
- [ ] страница с баджовете на потребителя
- [ ] страница с всички потребители, при натискане на потребител да се отваря нова страница с неговата информация
- [ ] бутон за напускане на страницата (log out)