import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '@/context/AuthContext';
import { useSettings } from '@/context/SettingsContext';
import { Label } from '@/components/ui/label';
import { Switch } from '@/components/ui/switch';
import { Separator } from '@/components/ui/separator';
import { motion } from 'framer-motion';
import { Moon, Sun, Volume2, Brain, Mail, MessageSquare, Bell } from 'lucide-react';
import AIFloatingButton from '@/components/AIFloatingButton';

const Settings = () => {
  const navigate = useNavigate();
  const { isAuthenticated, user } = useAuth();
  const { settings, updateSettings } = useSettings();

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/auth?mode=login');
    }
  }, [isAuthenticated, navigate]);

  if (!isAuthenticated) {
    return null;
  }

  const settingSections = [
    {
      title: 'Appearance',
      settings: [
        {
          id: 'darkMode',
          label: 'Dark Mode',
          description: 'Switch between light and dark theme',
          icon: settings.darkMode ? Moon : Sun,
          value: settings.darkMode,
        },
      ],
    },
    {
      title: 'Features',
      settings: [
        {
          id: 'ttsEnabled',
          label: 'Text-to-Speech',
          description: 'Enable voice reading of reminders and AI responses',
          icon: Volume2,
          value: settings.ttsEnabled,
        },
        {
          id: 'aiTipsEnabled',
          label: 'AI Tips',
          description: 'Receive personalized health tips from AI assistant',
          icon: Brain,
          value: settings.aiTipsEnabled,
        },
      ],
    },
    {
      title: 'Notifications',
      settings: [
        {
          id: 'browserNotifications',
          label: 'Browser Notifications',
          description: 'Show browser notifications for reminders',
          icon: Bell,
          value: settings.browserNotifications,
        },
        {
          id: 'emailNotifications',
          label: 'Email Reminders',
          description: 'Receive email notifications (requires EmailJS setup)',
          icon: Mail,
          value: settings.emailNotifications,
        },
        {
          id: 'smsNotifications',
          label: 'SMS Reminders',
          description: 'Receive SMS notifications (requires backend setup)',
          icon: MessageSquare,
          value: settings.smsNotifications,
        },
      ],
    },
  ];

  return (
    <div className="min-h-screen pb-20">
      <div className="container mx-auto px-4 py-8 max-w-3xl">
        {/* Header */}
        <motion.div
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5 }}
          className="mb-8"
        >
          <h1 className="text-4xl font-bold mb-2">Settings ⚙️</h1>
          <p className="text-muted-foreground text-lg">
            Customize your HealthMate experience
          </p>
        </motion.div>

        {/* User Info */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5, delay: 0.1 }}
          className="card-gradient rounded-xl p-6 border border-border shadow-soft mb-8"
        >
          <div className="flex items-center gap-4">
            <div className="w-16 h-16 rounded-full wellness-gradient flex items-center justify-center">
              <span className="text-2xl text-white font-bold">
                {user?.name?.charAt(0).toUpperCase()}
              </span>
            </div>
            <div>
              <h3 className="text-xl font-semibold">{user?.name}</h3>
              <p className="text-muted-foreground">{user?.email}</p>
            </div>
          </div>
        </motion.div>

        {/* Settings Sections */}
        <div className="space-y-6">
          {settingSections.map((section, sectionIndex) => (
            <motion.div
              key={section.title}
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.5, delay: 0.2 + sectionIndex * 0.1 }}
              className="card-gradient rounded-xl p-6 border border-border shadow-soft"
            >
              <h2 className="text-xl font-bold mb-4">{section.title}</h2>
              <div className="space-y-4">
                {section.settings.map((setting, index) => (
                  <div key={setting.id}>
                    {index > 0 && <Separator className="my-4" />}
                    <div className="flex items-center justify-between">
                      <div className="flex items-start gap-3 flex-1">
                        <div className="wellness-gradient p-2 rounded-lg mt-1">
                          <setting.icon className="w-5 h-5 text-white" />
                        </div>
                        <div className="flex-1">
                          <Label htmlFor={setting.id} className="text-base font-medium cursor-pointer">
                            {setting.label}
                          </Label>
                          <p className="text-sm text-muted-foreground mt-1">
                            {setting.description}
                          </p>
                        </div>
                      </div>
                      <Switch
                        id={setting.id}
                        checked={setting.value}
                        onCheckedChange={(checked) =>
                          updateSettings({ [setting.id]: checked })
                        }
                      />
                    </div>
                  </div>
                ))}
              </div>
            </motion.div>
          ))}
        </div>

        {/* Info Box */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5, delay: 0.6 }}
          className="mt-8 p-4 rounded-xl bg-muted/50 border border-border"
        >
          <p className="text-sm text-muted-foreground">
            <strong>📝 Note:</strong> Email and SMS notifications require additional setup.
            Configure EmailJS credentials in the code for email reminders, and set up a backend
            with Twilio for SMS functionality.
          </p>
        </motion.div>
      </div>

      <AIFloatingButton />
    </div>
  );
};

export default Settings;
