# CRM System

## Описание проекта
CRM-система предназначена для управления информацией о продавцах и их транзакциях. Система предоставляет возможности для создания, чтения, обновления и удаления данных о продавцах и транзакциях. Также система включает функции аналитики для обработки и анализа данных.

## Функциональность
- **Управление продавцами**:
    - Создание, обновление, удаление и получение информации о продавцах.
- **Управление транзакциями**:
    - Создание, обновление, удаление и получение информации о транзакциях.
- **Аналитика**:
    - Вывод самого продуктивного продавца за день, месяц, квартал, год.
    - Получение списка продавцов, у которых сумма всех транзакций за выбранный период меньше заданной суммы.

## Инструкции по сборке и запуску

### Требования
- Java 17 или выше
- Gradle
- PostgreSQL и H2 в режиме in-memory для тестирования

### Сборка проекта
1. Клонируйте репозиторий(либо распакуйте из вложенного в письмо файла):
   ```bash
   git clone https://github.com/gautamaPopados/CRMshift.git
   cd CRMshift
2. Скомпилируйте проект с помощью Gradle:
   ```bash
    ./gradlew build
### Запуск приложения
1. Убедитесь, что PostgreSQL установлен и запущен, или используйте H2.

2. Настройте параметры подключения к базе данных в application.properties:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/yourdb
    spring.datasource.username=your_username
    spring.datasource.password=your_password
3. Запустите приложение
    ```bash
   ./gradlew bootRun

### Примеры использования

1. Создание продавца
    ```http request
    POST /api/sellers
    Content-Type: application/json
    {
    "name": "Shifter Shiftov",
    "contactInfo": "shift@mail.ru"
    }
2. Получение информации о продавце
    ```http request
    GET /api/sellers/{id}
3. Получение списка всех продавцов
    ```http request
    GET /api/sellers
4. Создание транзакции
    ```http request
    POST /api/transactions
    Content-Type: application/json
    {
    "sellerId": 1,
    "amount": 1000,
    "paymentType": "CARD",
    "transactionDate": "2024-10-20T18:16:26"
    }
5. Получение самого продуктивного продавца (Период: DAY, MONTH, QUARTER, YEAR)
    ```http request
    GET /api/sellers/most-productive-in/{period}

### Необходимые зависимости
 - **spring-boot-starter-web** - для создания RESTful API.
 - **spring-boot-starter-data-jpa** - для работы с JPA и базами данных.
 - **lombok** - позволяет сократить шаблонный код
 - **junit-platform-launcher** - для тестов
 - **postgresql** - драйвер для подключения к PostgreSQL.
 - **h2** - для использования in-memory базы данных при тестировании. 

