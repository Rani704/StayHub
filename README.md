# StayHub

An Airbnb-style hotel booking platform built with Spring Boot — covering hotel/room
management, date-based inventory, dynamic pricing, JWT auth, and real payments
via Stripe (checkout, webhook confirmation, and refunds on cancellation).

## Features

- **Auth** — signup/login with JWT access + refresh tokens, role-based access
  (`GUEST`, `HOTEL_MANAGER`)
- **Hotel & room management** — hotel managers can create/update/activate hotels,
  manage rooms, and control per-date room inventory
- **Search & booking** — guests search hotels by city/date/room count, initiate a
  booking, add guests to it, and pay
- **Dynamic pricing** — a strategy chain (base, occupancy, surge, urgency, holiday)
  computes room price at search/booking time; a scheduled service keeps the
  cached minimum-price table fresh
- **Payments** — Stripe Checkout session per booking, webhook-verified
  confirmation, and automatic refund on cancellation
- **Reporting** — booking count / total / average revenue per hotel, for managers
- **API docs** — Swagger UI via springdoc-openapi

## Tech stack

| Layer      | Tech                                                              |
|------------|--------------------------------------------------------------------|
| Language   | Java 17                                                             |
| Framework  | Spring Boot, Spring Web, Spring Data JPA, Spring Security          |
| Database   | PostgreSQL                                                          |
| Auth       | JWT (jjwt)                                                          |
| Payments   | Stripe (Checkout Sessions, Webhooks, Refunds)                      |
| Docs       | springdoc-openapi (Swagger UI)                                     |
| Build      | Gradle                                                              |
| Other      | Lombok, ModelMapper                                                 |

## Architecture

Standard layered architecture:

```
controller  → REST endpoints, request/response mapping
service     → business logic (booking flow, pricing, payments)
repository  → Spring Data JPA repositories
entity      → JPA entities (Hotel, Room, Inventory, Booking, Guest, User, ...)
dto         → request/response payloads, mapped via ModelMapper
security    → JWT filter, auth service, security config
strategy    → pricing strategy chain (Base → Occupancy → Surge → Urgency → Holiday)
advice      → global exception handling, standard API response wrapper
```

Room availability is tracked per room, per date in an `Inventory` table
(rather than just a boolean flag on the room), which is what makes overlapping
date-range search and per-night dynamic pricing possible.

## Getting started

### Prerequisites

- Java 17+
- PostgreSQL running locally (or update the datasource URL below)
- A [Stripe](https://stripe.com) account (test mode is fine) for the secret key
  and a webhook signing secret

### 1. Clone and configure

```bash
git clone https://github.com/Rani704/StayHub.git
cd StayHub/stayhub
```

Create `src/main/resources/application.properties` (this file is gitignored —
it holds secrets, so it isn't committed) with:

```properties
spring.application.name=stayhub

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/stayhub
spring.datasource.username=postgres
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secretKey=replace_with_a_long_random_secret

# Stripe
stripe.secret.key=sk_test_your_key
stripe.webhook.secret=whsec_your_webhook_secret
```

### 2. Run it

```bash
./gradlew bootRun
```

The app starts on `http://localhost:8080`.

### 3. Explore the API

Swagger UI: `http://localhost:8080/swagger-ui.html`

### 4. Stripe webhook (local testing)

Forward Stripe events to your local server with the [Stripe CLI](https://stripe.com/docs/stripe-cli):

```bash
stripe listen --forward-to localhost:8080/webhook/payment
```

## API overview

| Area              | Endpoint                                    | Notes                          |
|-------------------|----------------------------------------------|---------------------------------|
| Auth              | `POST /auth/signup`                          | Create an account               |
|                   | `POST /auth/login`                           | Get access + refresh token      |
|                   | `POST /auth/refresh`                         | Refresh the access token        |
| Browse            | `GET /hotels/search`                         | Search hotels                   |
|                   | `GET /hotels/{hotelId}/info`                 | Hotel details                   |
| Booking flow      | `POST /bookings/init`                        | Start a booking                 |
|                   | `POST /bookings/{id}/addGuests`              | Attach guests                   |
|                   | `POST /bookings/{id}/payments`               | Get Stripe checkout URL         |
|                   | `POST /bookings/{id}/cancel`                 | Cancel + refund                 |
|                   | `GET /bookings/{id}/status`                  | Check booking status            |
| Profile           | `GET/PATCH /users/profile`                   | View/update profile             |
|                   | `GET /users/myBookings`                      | Booking history                 |
|                   | `.../users/guests`                           | Guest CRUD                      |
| Admin — hotels    | `POST/GET/PUT/DELETE /admin/hotels`          | Hotel CRUD (HOTEL_MANAGER only) |
|                   | `PATCH /admin/hotels/{id}/activate`          | Activate a hotel                |
|                   | `GET /admin/hotels/{id}/bookings`            | All bookings for a hotel        |
|                   | `GET /admin/hotels/{id}/reports`             | Revenue report                  |
| Admin — rooms     | `.../admin/hotels/{hotelId}/rooms`           | Room CRUD                       |
| Admin — inventory | `.../admin/inventory/rooms/{roomId}`         | View/update date-wise inventory |
| Webhook           | `POST /webhook/payment`                      | Stripe event listener           |

Full request/response shapes are in Swagger UI once the app is running.

## Roadmap / known gaps

- No dedicated `Payment`/transaction entity yet — payment state currently
  lives on `Booking.paymentSessionId`, so there's no history of multiple
  attempts or partial refunds
- No request-body validation (`@Valid` / bean validation) on DTOs yet
- Test coverage is limited to the default Spring context-load test

## License

Personal/portfolio project — feel free to browse and reuse ideas.
