# КАК ЗАПУСТИТЬ
# открыть папку для проекта в bash с правами администратора

```commandline
git clone git@github.com:Praepost/vita.git
```

```commandline
chmod +x ./entrypoint.sh
```

```commandline
docker-compose up --build

```

# ТЗ

Спроектировать и разработать систему регистрации и обработки пользовательских заявок.
Пользователь посредством системы может подавать заявки оператору на рассмотрение. Оператор
может просматривать пользовательские заявки и принимать или отклонять их. Администратор
управляет правами доступа.
• Спроектировать и разработать back-приложение
• Спроектировать и разработать Базу данных

Функции приложения
• Создать заявку (Заявка помимо прочих системных полей состоит из статуса и текстового
обращения пользователя)
• Отправить заявку оператору на рассмотрение
• Просмотреть список заявок с возможностью сортировки по дате создания в оба
направления (как от самой старой к самой новой, так и наоборот) и пагинацией по 5
элементов
• Посмотреть заявку
• Принять заявку
• Отклонить заявку
• Просмотреть список пользователей
• Назначить права оператора

В системе предусмотрены 3 роли:
• Пользователь
• Оператор
• Администратор

У пользователя системы может быть одновременно несколько ролей, например, «Оператор» и
«Администратор».
У заявки пользователя предусмотрено 4 состояния:
• черновик
• отправлено
• принято
• отклонено

Пользователь может
• создавать заявки

• просматривать созданные им заявки с возможностью сортировки по дате создания в оба
направления (как от самой старой к самой новой, так и наоборот) и пагинацией по 5
элементов
• редактировать созданные им заявки в статусе «черновик»
• отправлять заявки на рассмотрение оператору.
Пользователь НЕ может:
• редактировать отправленные на рассмотрение заявки
• видеть заявки других пользователей
• принимать заявки
• отклонять заявки
• назначать права
• смотреть список пользователей

Оператор может
• Просматривать все отправленные на рассмотрение заявки с возможностью сортировки
по дате создания в оба направления (как от самой старой к самой новой, так и наоборот)
и пагинацией по 5 элементов
• Просматривать отправленные заявки только конкретного пользователя по его
имени/части имени (у пользователя, соотетственно, должно быть поле name) с
возможностью сортировки по дате создания в оба направления (как от самой старой к
самой новой, так и наоборот) и пагинацией по 5 элементов
• Принимать заявки
• Отклонять заявки
Оператор НЕ может
• создавать заявки
• просматривать заявки в статусе отличном от «отправлено»
• редактировать заявки
• назначать права

Администратор может
• смотреть список пользователей
• искать конкретного пользователя по его имени/части имени
• назначать пользователям права оператора
Администратор НЕ может
• создавать заявки

• просматривать заявки
• редактировать заявки
• принимать заявки
• отклонять заявки

Технические требования к приложению
• Java 1.8/Java 11
• Использовать архитектуру REST
• Использовать Spring Boot
• Использовать Spring Security
• Использовать Hibernate
• Использовать реляционную БД (MS SQL, MS SQL Lite, PostgreSQL, MariaBD)
• Создание пользователей и ролей не предусмотрено в этой системе. Подразумевается, что
данные об учетных записях пользователей и роли уже есть в БД.
• В случае просмотра заявки оператором текст заявки выводить со знаком &lt;-&gt; после
каждого символа. Пример: Пользователь отправил на рассмотрение заявку с текстом
«Мне нужна помощь», а оператор на экране видит текст в формате «М-н-е- -н-у-ж-н-а- -п-
о-м-о-щ-ь».
