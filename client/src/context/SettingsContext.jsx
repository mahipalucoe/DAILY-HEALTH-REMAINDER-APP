import React, { createContext, useContext, useState, useEffect } from 'react';

const defaultSettings = {
  darkMode: false,
  ttsEnabled: true,
  aiTipsEnabled: true,
  emailNotifications: false,
  smsNotifications: false,
  browserNotifications: true,
};

const SettingsContext = createContext(undefined);

export const SettingsProvider = ({ children }) => {
  const [settings, setSettings] = useState(defaultSettings);

  useEffect(() => {
    const savedSettings = localStorage.getItem('healthmate_settings');
    if (savedSettings) {
      const parsed = JSON.parse(savedSettings);
      setSettings(parsed);
      
      if (parsed.darkMode) {
        document.documentElement.classList.add('dark');
      }
    }
  }, []);

  const updateSettings = (newSettings) => {
    setSettings((prev) => {
      const updated = { ...prev, ...newSettings };
      localStorage.setItem('healthmate_settings', JSON.stringify(updated));
      
      if (newSettings.darkMode !== undefined) {
        if (newSettings.darkMode) {
          document.documentElement.classList.add('dark');
        } else {
          document.documentElement.classList.remove('dark');
        }
      }
      
      return updated;
    });
  };

  const toggleDarkMode = () => {
    updateSettings({ darkMode: !settings.darkMode });
  };

  return (
    <SettingsContext.Provider value={{ settings, updateSettings, toggleDarkMode }}>
      {children}
    </SettingsContext.Provider>
  );
};

export const useSettings = () => {
  const context = useContext(SettingsContext);
  if (context === undefined) {
    throw new Error('useSettings must be used within a SettingsProvider');
  }
  return context;
};