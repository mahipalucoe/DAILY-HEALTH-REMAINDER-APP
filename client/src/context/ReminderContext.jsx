import React, { createContext, useContext, useState, useEffect } from 'react';
import { scheduleReminder } from '@/utils/notifications';

const ReminderContext = createContext(undefined);

export const ReminderProvider = ({ children }) => {
  const [reminders, setReminders] = useState([]);

  useEffect(() => {
    const savedReminders = localStorage.getItem('healthmate_reminders');
    if (savedReminders) {
      setReminders(JSON.parse(savedReminders));
    } else {
      const defaultReminders = [
        {
          id: '1',
          title: 'Morning Water',
          time: '08:00',
          type: 'water',
          repeat: 'daily',
          notes: 'Start your day with a glass of water',
          completed: false,
          createdAt: new Date().toISOString(),
        },
        {
          id: '2',
          title: 'Morning Exercise',
          time: '09:00',
          type: 'exercise',
          repeat: 'daily',
          notes: '30 minutes of cardio or yoga',
          completed: false,
          createdAt: new Date().toISOString(),
        },
        {
          id: '3',
          title: 'Take Vitamins',
          time: '12:00',
          type: 'medication',
          repeat: 'daily',
          notes: 'Don\'t forget your daily vitamins',
          completed: false,
          createdAt: new Date().toISOString(),
        },
      ];
      setReminders(defaultReminders);
      localStorage.setItem('healthmate_reminders', JSON.stringify(defaultReminders));
    }
  }, []);

  const saveReminders = (newReminders) => {
    setReminders(newReminders);
    localStorage.setItem('healthmate_reminders', JSON.stringify(newReminders));
  };

  const addReminder = (reminder) => {
    const newReminder = {
      ...reminder,
      id: `reminder_${Date.now()}`,
      completed: false,
      createdAt: new Date().toISOString(),
    };

    const updatedReminders = [...reminders, newReminder];
    saveReminders(updatedReminders);
    scheduleReminder(newReminder);
  };

  const updateReminder = (id, updates) => {
    const updatedReminders = reminders.map((r) =>
      r.id === id ? { ...r, ...updates } : r
    );
    saveReminders(updatedReminders);
  };

  const deleteReminder = (id) => {
    const updatedReminders = reminders.filter((r) => r.id !== id);
    saveReminders(updatedReminders);
  };

  const toggleComplete = (id) => {
    const updatedReminders = reminders.map((r) =>
      r.id === id ? { ...r, completed: !r.completed } : r
    );
    saveReminders(updatedReminders);
  };

  const getTodayReminders = () => {
    return reminders.filter((r) => r.repeat === 'daily');
  };

  const getCompletedCount = () => {
    return reminders.filter((r) => r.completed).length;
  };

  const getStreak = () => {
    const completedToday = getCompletedCount();
    return completedToday > 0 ? Math.floor(completedToday / 2) + 1 : 0;
  };

  return (
    <ReminderContext.Provider
      value={{
        reminders,
        addReminder,
        updateReminder,
        deleteReminder,
        toggleComplete,
        getTodayReminders,
        getCompletedCount,
        getStreak,
      }}
    >
      {children}
    </ReminderContext.Provider>
  );
};

export const useReminders = () => {
  const context = useContext(ReminderContext);
  if (context === undefined) {
    throw new Error('useReminders must be used within a ReminderProvider');
  }
  return context;
};
