import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '@/context/AuthContext';
import { useReminders } from '@/context/ReminderContext';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, LineElement, PointElement, Title, Tooltip, Legend, ArcElement } from 'chart.js';
import { Bar, Doughnut } from 'react-chartjs-2';
import { motion } from 'framer-motion';
import { TrendingUp, Activity, Target } from 'lucide-react';
import AIFloatingButton from '@/components/AIFloatingButton';

ChartJS.register(CategoryScale, LinearScale, BarElement, LineElement, PointElement, Title, Tooltip, Legend, ArcElement);

const Stats = () => {
  const navigate = useNavigate();
  const { isAuthenticated } = useAuth();
  const { reminders, getCompletedCount, getStreak } = useReminders();

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/auth?mode=login');
    }
  }, [isAuthenticated, navigate]);

  if (!isAuthenticated) {
    return null;
  }

  // Weekly progress data (mock data for visualization)
  const weeklyData = {
    labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
    datasets: [
      {
        label: 'Completed Reminders',
        data: [3, 2, 4, 3, 5, 4, getCompletedCount()],
        backgroundColor: 'hsl(180, 65%, 55%)',
        borderColor: 'hsl(180, 65%, 55%)',
        borderWidth: 2,
        borderRadius: 8,
      },
    ],
  };

  // Reminder types distribution
  const typeData = {
    labels: ['Water 💧', 'Exercise 🏋️', 'Medication 💊', 'Sleep 😴', 'Meditation 🧘'],
    datasets: [
      {
        data: [
          reminders.filter((r) => r.type === 'water').length,
          reminders.filter((r) => r.type === 'exercise').length,
          reminders.filter((r) => r.type === 'medication').length,
          reminders.filter((r) => r.type === 'sleep').length,
          reminders.filter((r) => r.type === 'meditation').length,
        ],
        backgroundColor: [
          'hsl(180, 65%, 55%)',
          'hsl(15, 85%, 70%)',
          'hsl(270, 55%, 70%)',
          'hsl(220, 60%, 65%)',
          'hsl(140, 60%, 60%)',
        ],
        borderWidth: 2,
        borderColor: 'hsl(var(--background))',
      },
    ],
  };

  const chartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        display: false,
      },
    },
    scales: {
      y: {
        beginAtZero: true,
        grid: {
          color: 'hsl(var(--border))',
        },
      },
      x: {
        grid: {
          display: false,
        },
      },
    },
  };

  const doughnutOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: 'bottom',
      },
    },
  };

  const completionRate = reminders.length > 0 ? Math.round((getCompletedCount() / reminders.length) * 100) : 0;

  return (
    <div className="min-h-screen pb-20">
      <div className="container mx-auto px-4 py-8">
        {/* Header */}
        <motion.div
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5 }}
          className="mb-8"
        >
          <h1 className="text-4xl font-bold mb-2">Your Progress 📊</h1>
          <p className="text-muted-foreground text-lg">
            Track your wellness journey and celebrate your achievements
          </p>
        </motion.div>

        {/* Key Metrics */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5, delay: 0.1 }}
          className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8"
        >
          <div className="card-gradient rounded-xl p-6 border border-border shadow-soft">
            <div className="flex items-center gap-4">
              <div className="wellness-gradient p-4 rounded-xl">
                <TrendingUp className="w-8 h-8 text-white" />
              </div>
              <div>
                <div className="text-3xl font-bold gradient-text">
                  {getStreak()}
                </div>
                <p className="text-muted-foreground">Day Streak 🔥</p>
              </div>
            </div>
          </div>

          <div className="card-gradient rounded-xl p-6 border border-border shadow-soft">
            <div className="flex items-center gap-4">
              <div className="bg-secondary p-4 rounded-xl">
                <Activity className="w-8 h-8 text-white" />
              </div>
              <div>
                <div className="text-3xl font-bold text-secondary">
                  {getCompletedCount()}
                </div>
                <p className="text-muted-foreground">Completed Today</p>
              </div>
            </div>
          </div>

          <div className="card-gradient rounded-xl p-6 border border-border shadow-soft">
            <div className="flex items-center gap-4">
              <div className="bg-accent p-4 rounded-xl">
                <Target className="w-8 h-8 text-white" />
              </div>
              <div>
                <div className="text-3xl font-bold text-accent">
                  {completionRate}%
                </div>
                <p className="text-muted-foreground">Completion Rate</p>
              </div>
            </div>
          </div>
        </motion.div>

        {/* Charts */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          {/* Weekly Progress */}
          <motion.div
            initial={{ opacity: 0, x: -20 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.5, delay: 0.2 }}
            className="card-gradient rounded-xl p-6 border border-border shadow-soft"
          >
            <h2 className="text-2xl font-bold mb-4">Weekly Progress</h2>
            <div className="h-64">
              <Bar data={weeklyData} options={chartOptions} />
            </div>
          </motion.div>

          {/* Reminder Types */}
          <motion.div
            initial={{ opacity: 0, x: 20 }}
            animate={{ opacity: 1, x: 0 }}
            transition={{ duration: 0.5, delay: 0.3 }}
            className="card-gradient rounded-xl p-6 border border-border shadow-soft"
          >
            <h2 className="text-2xl font-bold mb-4">Reminder Types</h2>
            <div className="h-64">
              <Doughnut data={typeData} options={doughnutOptions} />
            </div>
          </motion.div>
        </div>

        {/* Insights */}
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5, delay: 0.4 }}
          className="mt-8 wellness-gradient rounded-xl p-8 shadow-glow"
        >
          <h2 className="text-2xl font-bold text-white mb-4">💡 Insights</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-white">
            <div>
              <p className="text-white/90">
                <strong>Great progress!</strong> You're maintaining a {getStreak()}-day streak.
                Keep up the consistency!
              </p>
            </div>
            <div>
              <p className="text-white/90">
                <strong>Tip:</strong> Set reminders at consistent times each day to build
                stronger habits.
              </p>
            </div>
          </div>
        </motion.div>
      </div>

      <AIFloatingButton />
    </div>
  );
};

export default Stats;