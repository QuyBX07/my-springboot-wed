// src/App.js
import React from 'react';
import './styles/main.css';     // Import CSS file
import Navbar from './components/Navbar'; // Import Navbar component
import RecipeSuggestionList from './components/RecipeSuggestionList';

import './utils/uikit.js';      // Import JavaScript file (nếu có thể chạy mà không cần import vào mỗi component)

// App component
function App() {
  return (
    <div className="App">
      <Navbar />   {/* Render Navbar */}
      <main>
        {/* Nội dung khác của ứng dụng sẽ đặt ở đây */}
        <RecipeSuggestionList/>
      </main>
    </div>
  );
}

export default App;
