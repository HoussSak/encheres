import React from 'react'
import '../main/main.css'

const Main = () => {

  return (

      <div className='container'>
          <div><h2>Liste des enchères</h2></div>
          <div>
              <label>Filtres :</label>
              <input type=''></input>
              <button>Rechercher</button>
          </div>
          <div>
              <label>Catégorie :</label>
              <select>
                  <option value="option1">Option 1</option>
                  <option value="option2">Option 2</option>
                  <option value="option3">Option 3</option>
              </select>
          </div>
      </div>

  )
}

export default Main

