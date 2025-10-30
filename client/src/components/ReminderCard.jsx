import { Button } from '@/components/ui/button';
import { Clock, Check, Trash2 } from 'lucide-react';
import { motion } from 'framer-motion';

const reminderIcons = {
  water: '💧',
  exercise: '🏋️',
  medication: '💊',
  sleep: '😴',
  meditation: '🧘',
};

const ReminderCard = ({ reminder, onToggle, onDelete }) => {
  return (
    <motion.div
      initial={{ opacity: 0, scale: 0.95 }}
      animate={{ opacity: 1, scale: 1 }}
      transition={{ duration: 0.3 }}
      className={`card-gradient rounded-xl p-5 border-2 transition-smooth shadow-soft hover:shadow-elevated ${
        reminder.completed
          ? 'border-primary/30 bg-primary/5'
          : 'border-border hover:border-primary/50'
      }`}
    >
      <div className="flex items-start gap-4">
        {/* Icon */}
        <div
          className={`text-4xl flex-shrink-0 transition-smooth ${
            reminder.completed ? 'opacity-50 grayscale' : 'animate-float'
          }`}
        >
          {reminderIcons[reminder.type]}
        </div>

        {/* Content */}
        <div className="flex-1 min-w-0">
          <div className="flex items-start justify-between gap-2 mb-2">
            <h3
              className={`font-semibold text-lg transition-smooth ${
                reminder.completed ? 'line-through text-muted-foreground' : ''
              }`}
            >
              {reminder.title}
            </h3>
            <div className="flex items-center gap-1 text-sm text-muted-foreground flex-shrink-0">
              <Clock className="w-4 h-4" />
              {reminder.time}
            </div>
          </div>

          {reminder.notes && (
            <p className="text-sm text-muted-foreground mb-3">{reminder.notes}</p>
          )}

          <div className="flex items-center gap-2">
            <Button
              size="sm"
              variant={reminder.completed ? 'outline' : 'default'}
              onClick={() => onToggle(reminder.id)}
              className="transition-smooth"
            >
              <Check className="w-4 h-4" />
              {reminder.completed ? 'Completed' : 'Mark Done'}
            </Button>

            <Button
              size="sm"
              variant="ghost"
              onClick={() => onDelete(reminder.id)}
              className="text-destructive hover:text-destructive hover:bg-destructive/10"
            >
              <Trash2 className="w-4 h-4" />
            </Button>
          </div>
        </div>
      </div>
    </motion.div>
  );
};

export default ReminderCard;