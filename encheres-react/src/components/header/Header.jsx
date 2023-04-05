import React from 'react';
import '../header/header.css';

const Header = () => {
  return (

    <div className='header-container'>
      <h1 className='title'>ENI-Enchères</h1>
      <div className='register'><a href='#'>S'inscrire</a></div>
      <div className='login'><a href='#'>Se connecter</a></div>
    </div>
  )
}

export default Header;
