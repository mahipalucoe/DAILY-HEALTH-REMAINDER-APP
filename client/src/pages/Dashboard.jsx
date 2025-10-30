import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { useAuth } from '@/context/AuthContext';
import { useReminders } from '@/context/ReminderContext';
import { useSettings } from '@/context/SettingsContext';
import { useToast } from '@/hooks/use-toast';
import { speak } from '@/utils/tts';
import { requestNotificationPermission } from '@/utils/notifications';
import ReminderCard from '@/components/ReminderCard';
import ReminderModal from '@/components/ReminderModal';
import AIFloatingButton from '@/components/AIFloatingButton';
import { Plus, Flame, Sparkles } from 'lucide-react';
import { motion } from 'framer-motion';

const motivationalQuotes = [
  'Your health is an investment, not an expense.',
  'Small daily improvements lead to stunning results.',
  'Take care of your body. It\'s the only place you have to live.',
  'The greatest wealth is health.',
  'Every step you take is progress.',
  'Consistency is the key to success.',
];

const Dashboard = () => {
  const navigate = useNavigate();
  const { user, isAuthenticated } = useAuth();
  const { reminders, toggleComplete, deleteReminder, getCompletedCount, getStreak } = useReminders();
  const { settings } = useSettings();
  const { toast } = useToast();

  const [modalOpen, setModalOpen] = useState(false);
  const [quote] = useState(
    motivationalQuotes[Math.floor(Math.random() * motivationalQuotes.length)]
  );

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/auth?mode=login');
    }
  }, [isAuthenticated, navigate]);

  useEffect(() => {
    requestNotificationPermission();

    if (settings.ttsEnabled && reminders.length > 0) {
      const uncompleted = reminders.filter((r) => !r.completed);
      if (uncompleted.length > 0) {
        speak(`You have ${uncompleted.length} reminders for today.`);
      }
    }
  }, []);

  const handleToggle = (id) => {
    toggleComplete(id);
    const reminder = reminders.find((r) => r.id === id);
    if (reminder && !reminder.completed) {
      toast({
        title: 'Great job! 🎉',
        description: 'Keep up the consistency!',
      });
      if (settings.ttsEnabled) {
        speak('Great job! Keep up the consistency!');
      }
    }
  };

  const handleDelete = (id) => {
    deleteReminder(id);
    toast({
      title: 'Reminder deleted',
      description: 'The reminder has been removed.',
    });
  };

  const todayReminders = reminders.filter((r) => r.repeat === 'daily');
  const streak = getStreak();

  if (!isAuthenticated) {
    return null;
  }

  return (
    <div className="min-h-screen pb-20">
      <div className="container mx-auto px-4 py-8">
        <motion.div
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5 }}
          className="mb-8"
        >
          <div className="flex items-center justify-between mb-4">
            <div>
              <h1 className="text-4xl font-bold mb-2">
                Welcome back, {user?.name}! 👋
              </h1>
              <p className="text-muted-foreground text-lg">
                Let's maintain your healthy habits today
              </p>
            </div>

            <div className="flex items-center gap-4">
              <div className="text-center card-gradient px-6 py-3 rounded-xl shadow-soft border border-border">
                <div className="flex items-center gap-2 mb-1">
                  <Flame className="w-5 h-5 text-secondary" />
                  <span className="text-3xl font-bold gradient-text">
                    {streak}
                  </span>
                </div>
                <p className="text-xs text-muted-foreground">Day Streak</p>
              </div>
            </div>
          </div>

          <motion.div
            initial={{ opacity: 0, scale: 0.95 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ duration: 0.5, delay: 0.2 }}
            className="wellness-gradient rounded-xl p-6 shadow-glow"
          >
            <div className="flex items-start gap-3">
              <Sparkles className="w-6 h-6 text-white flex-shrink-0 mt-1" />
              <div>
                <p className="text-white text-lg font-medium italic">{quote}</p>
              </div>
            </div>
          </motion.div>
        </motion.div>

        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5, delay: 0.3 }}
          className="mb-6"
        >
          <Button
            onClick={() => setModalOpen(true)}
            variant="gradient"
            size="lg"
            className="shadow-elevated hover:shadow-glow"
          >
            <Plus className="w-5 h-5" />
            Add New Reminder
          </Button>
        </motion.div>

        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5, delay: 0.4 }}
          className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-8"
        >
          <div className="card-gradient rounded-xl p-6 border border-border shadow-soft">
            <div className="text-3xl font-bold gradient-text mb-1">
              {todayReminders.length}
            </div>
            <p className="text-muted-foreground">Today's Reminders</p>
          </div>
          <div className="card-gradient rounded-xl p-6 border border-border shadow-soft">
            <div className="text-3xl font-bold gradient-text mb-1">
              {getCompletedCount()}
            </div>
            <p className="text-muted-foreground">Completed</p>
          </div>
          <div className="card-gradient rounded-xl p-6 border border-border shadow-soft">
            <div className="text-3xl font-bold gradient-text mb-1">
              {todayReminders.length > 0
                ? Math.round((getCompletedCount() / todayReminders.length) * 100)
                : 0}
              %
            </div>
            <p className="text-muted-foreground">Completion Rate</p>
          </div>
        </motion.div>

        <div className="space-y-4">
          <h2 className="text-2xl font-bold mb-4">Your Reminders</h2>
          {reminders.length === 0 ? (
            <motion.div
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              className="text-center py-12 card-gradient rounded-xl border border-border"
            >
              <p className="text-muted-foreground text-lg">
                No reminders yet. Create your first one to get started!
              </p>
            </motion.div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {reminders.map((reminder, index) => (
                <motion.div
                  key={reminder.id}
                  initial={{ opacity: 0, y: 20 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ duration: 0.3, delay: index * 0.1 }}
                >
                  <ReminderCard
                    reminder={reminder}
                    onToggle={handleToggle}
                    onDelete={handleDelete}
                  />
                </motion.div>
              ))}
            </div>
          )}
        </div>
      </div>

      <ReminderModal open={modalOpen} onClose={() => setModalOpen(false)} />
      <AIFloatingButton />
    </div>
  );
};

export default Dashboard;