# Use Node.js image
FROM node:18-alpine

# Set working directory inside the container
WORKDIR /app

# Copy package files first to leverage Docker cache for dependencies
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the app source code
COPY . .

# Expose port 3000 (React default)
EXPOSE 3000

# Start the React development server
CMD ["npm", "start"]