# 🚀 Project Hyperion - TODO List

## 🛠️ Global Configuration & Tech Stack
- [ ] Configure the local PostgreSQL database in `application.yml`
- [ ] Connect and test database persistence with Hibernate/JPA
- [ ] Configure the Apache Tiles 3 rendering engine (layouts, header, footer)
- [ ] Verify global integration of CSS stylesheets and static assets

---

## 🔵🛒 "Cart" Module (Shopping Cart) & 🟡🎨 UI Design — **[`SIMON`]**
*Manage product selection, temporary storage, validation before checkout, and global application styling.*
- [ ] Create the JSP structure for the cart in `src/main/webapp/WEB-INF/jsp/`
- [ ] Set up `importTags.jsp` with required JSTL directives (especially `<c:forEach>`)
- [ ] Implement `PanelController` to handle add/remove cart actions
- [ ] Create session-based logic to persist cart items for the user
- [ ] Manage dynamic rendering of the item list with details (name, quantity, unit price, subtotal)
- [ ] Dynamically calculate and display the total cart amount
- [ ] Implement validation rules (positive quantities only, stock level checks)
- [ ] Add a checkout button to proceed to the purchase flow
- [ ] **[Design]** Design and build the global CSS layout (header, footer, navigation bar)
- [ ] **[Design]** Create responsive UI components for the product catalog grid and forms

---

## 🟢📦 "Catalog" Module (Product Management) — **[`ALTIN`]**
*Display available items for purchase.*
- [ ] Design the `Product` JPA entity (id, name, description, price, stock, image)
- [ ] Create `ProductRepository` to communicate with PostgreSQL
- [ ] Develop the JSP page displaying the product list in a grid/card layout
- [ ] Add a detailed view page for individual products
- [ ] Implement search filters or product categories (optional)

---

## 🔴👥 "Security" Module (Users & Authentication) — **[`NISRINE`]**
*Allow customers to register and log in securely.*
- [ ] Design the `User` entity (id, name, email, encrypted password, shipping address)
- [ ] Create sign-up and login screens (JSP + form validation)
- [ ] Implement basic session-based security or Spring Security
- [ ] Associate a shopping cart with the currently logged-in user

---

## 💳 "Orders & Checkout" Module — * Shared / Next Steps *
*Finalize purchases and record transactions.*
- [ ] Design `Order` and `CommandLine` entities (mapping cart items to purchase history)
- [ ] Create the order summary screen prior to final confirmation
- [ ] Decrease product stock levels in the database upon a successful order
- [ ] Display a success screen with the generated order number