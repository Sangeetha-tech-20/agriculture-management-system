# Agriculture Management System

A full-stack web application for managing agricultural crops and irrigation systems with user authentication.

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Node.js 14+

### Database Setup
1. Start MySQL service
2. Create database: `agriculture_db`
3. Tables will be created automatically on first run

### Running the Application

#### Option 1: Automated Startup (Recommended)
Run the startup script:
```batch
start-app.bat
```

#### Option 2: Manual Startup

1. **Start Backend:**
   ```batch
   mvn spring-boot:run
   ```
   Note the port number (e.g., 61305)

2. **Update Frontend API URL:**
   - Open `index.html`
   - Change `API_BASE_URL` to match backend port:
   ```javascript
   const API_BASE_URL = 'http://localhost:61305';
   ```

3. **Start Frontend:**
   ```batch
   node server.js
   ```

### Access the Application
- **Frontend:** http://localhost:3000
- **Backend API:** http://localhost:8080

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

### Crops
- `POST /api/crops` - Add new crop
- `GET /api/crops/user/{userId}` - Get crops by user
- `PUT /api/crops/{id}` - Update crop
- `DELETE /api/crops/{id}` - Delete crop

### Irrigation
- `POST /api/irrigation` - Schedule irrigation
- `GET /api/irrigation/user/{userId}` - Get irrigation schedules by user
- `GET /api/irrigation/{id}` - Get irrigation by ID
- `PUT /api/irrigation/{id}` - Update irrigation
- `DELETE /api/irrigation/{id}` - Delete irrigation
- `GET /api/irrigation/upcoming/{userId}` - Get upcoming irrigations

## 🐛 Troubleshooting

### "Failed to fetch" Errors
1. Ensure backend is running
2. Check the port number in backend console
3. Update `API_BASE_URL` in `index.html`
4. Verify CORS settings

### Database Connection Issues
1. Ensure MySQL is running
2. Check database credentials in `application.properties`
3. Verify database name: `agriculture_db`

### Port Conflicts
- Spring Boot uses random ports by default
- Check console output for actual port number
- Update frontend API_BASE_URL accordingly

## Configuration

Update the following in `application.properties`:

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/agriculture_db
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT
jwt.secret=your_jwt_secret_key_here
jwt.expiration=86400000

# External APIs (for future implementation)
openweather.api.key=your_openweather_api_key
market.api.key=your_market_api_key
```

## 🧪 Testing

### API Testing
Use tools like Postman or curl to test endpoints:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Test","lastName":"User","email":"test@example.com","password":"password","phone":"1234567890","farmSize":10.5}'
```

### Frontend Testing
1. Open http://localhost:3000
2. Register a new account
3. Login and test crop/irrigation features

## 📝 Development Notes

- Backend runs on random ports (Spring Boot default)
- Always check console for actual port number
- Update frontend API_BASE_URL when port changes
- Database schema updates automatically

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/agriculture/
│   │       ├── AgricultureApplication.java
│   │       ├── config/
│   │       │   ├── JwtAuthenticationFilter.java
│   │       │   ├── JwtUtil.java
│   │       │   └── SecurityConfig.java
│   │       ├── controller/
│   │       │   ├── AuthController.java
│   │       │   ├── CropController.java
│   │       │   └── IrrigationController.java
│   │       ├── dto/
│   │       │   ├── ApiResponse.java
│   │       │   ├── LoginRequest.java
│   │       │   └── RegisterRequest.java
│   │       ├── entity/
│   │       │   ├── Crop.java
│   │       │   ├── Irrigation.java
│   │       │   └── User.java
│   │       ├── repository/
│   │       │   ├── CropRepository.java
│   │       │   ├── IrrigationRepository.java
│   │       │   └── UserRepository.java
│   │       └── service/
│   │           ├── CropService.java
│   │           ├── IrrigationService.java
│   │           └── UserService.java
│   └── resources/
│       ├── application.properties
│       └── schema.sql
└── index.html
```

## Usage

1. Register a new user account
2. Login with your credentials
3. Add crops to your farm
4. Schedule irrigation activities
5. Monitor weather and market information
6. View government schemes

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.