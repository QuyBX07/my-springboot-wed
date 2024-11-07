// src/components/RecipeList.js
import React, { useState, useEffect } from 'react';
import RecipeCard from './RecipeSuggestionCard';

function RecipeSuggestionList() {
  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true); // State để hiển thị loading

  useEffect(() => {
    // Gọi API để lấy dữ liệu
    fetch('http://localhost:8083/foodwed/suggestion/1&1')
      .then((response) => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then((data) => {
      console.log("API Data:", data);
        // Kiểm tra nếu API trả về dữ liệu trong `data.result`
        if (data.status === "success" && data.result) {
          setRecipes(data.result);
        } else {
          console.error("API response is not in the expected format.");
        }
        setLoading(false); // Tắt loading khi dữ liệu đã tải xong
      })
      .catch((error) => {
        console.error("Failed to fetch data:", error);
        setLoading(false);
      });
  }, []);

  // Hiển thị loading khi dữ liệu chưa tải xong
  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className="uk-section uk-section-muted">
      <div className="uk-container">
        <h3>Other Recipes You May Like</h3>
        <div className="uk-child-width-1-2 uk-child-width-1-3@s uk-child-width-1-4@m uk-margin-medium-top" data-uk-grid>
          {recipes.map((recipe) => (
            <RecipeCard key={recipe.id} name={recipe.name} image={recipe.image} />
          ))}
        </div>
      </div>
    </div>
  );
}

export default RecipeSuggestionList;
