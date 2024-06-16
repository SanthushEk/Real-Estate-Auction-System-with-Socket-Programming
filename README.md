Project Title: Real Estate Auction System with Socket Programming

Description:

I developed a Real Estate Auction System utilizing socket programming to enable real-time bidding on properties. The project includes both a client-server model and a publisher-subscriber model, ensuring efficient real-time communication and updates.

Client-Server Model:

Functionality: Buyers can view and place bids on properties, while sellers can list property details.
Implementation: The server listens on port 2021, handling multiple connections concurrently. Each property has a base price and unique code, stored in a CSV file read at startup.
Bidding Process: Bidding begins once a client bids higher than the base price. Subsequent bids must exceed the current highest bid. The auction ends at a specified time but extends if a bid is made within the last minute.
Output Handling: Clients query the server to get the current highest bid for a property or to place new bids. The server responds with the updated bid or error codes for invalid requests.
Publisher-Subscriber Model:

Functionality: Sellers can publish property updates, and buyers can subscribe to receive these updates and bid change notifications.
Implementation: The server listens on port 2022 for this model. Subscribers register to receive updates for specific properties. Whenever a publisher updates property information or a bid changes, subscribers are immediately notified.
Real-Time Updates: The system ensures subscribers receive instant notifications about new property information and bid changes.
Technical Details:

Concurrency: The server handles multiple client connections simultaneously, ensuring efficient parallel processing.
Unique Client IDs: Ensures each client has a unique ID to maintain session integrity and proper bid tracking.
Protocol Design: Developed clear protocols for client-server and publisher-subscriber communication, ensuring reliable data exchange and real-time updates.
Achievements:

This project provided practical experience in real-time systems and network programming, enhancing my skills in Java, socket programming, concurrency, and network protocol design. The system successfully facilitated real-time bidding and property updates, demonstrating the effectiveness of the client-server and publisher-subscriber models in a dynamic auction environment.
