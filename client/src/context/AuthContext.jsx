import React, { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext(undefined);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('healthmate_token');
    const userData = localStorage.getItem('healthmate_user');
    
    if (token && userData) {
      setUser(JSON.parse(userData));
    }
  }, []);

  const login = async (email, password) => {
    const users = JSON.parse(localStorage.getItem('healthmate_users') || '[]');
    const foundUser = users.find((u) => u.email === email && u.password === password);

    if (foundUser) {
      const { password: _, ...userWithoutPassword } = foundUser;
      const token = btoa(`${email}:${Date.now()}`);
      
      localStorage.setItem('healthmate_token', token);
      localStorage.setItem('healthmate_user', JSON.stringify(userWithoutPassword));
      setUser(userWithoutPassword);
      return true;
    }

    return false;
  };

  const signup = async (name, email, password) => {
    const users = JSON.parse(localStorage.getItem('healthmate_users') || '[]');
    
    if (users.find((u) => u.email === email)) {
      return false;
    }

    const newUser = {
      id: `user_${Date.now()}`,
      name,
      email,
      password,
    };

    users.push(newUser);
    localStorage.setItem('healthmate_users', JSON.stringify(users));

    const { password: _, ...userWithoutPassword } = newUser;
    const token = btoa(`${email}:${Date.now()}`);
    
    localStorage.setItem('healthmate_token', token);
    localStorage.setItem('healthmate_user', JSON.stringify(userWithoutPassword));
    setUser(userWithoutPassword);
    return true;
  };

  const logout = () => {
    localStorage.removeItem('healthmate_token');
    localStorage.removeItem('healthmate_user');
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        login,
        signup,
        logout,
        isAuthenticated: !!user,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};