import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { MessageCircle } from 'lucide-react';
import { motion } from 'framer-motion';

const AIFloatingButton = () => {
  return (
    <motion.div
      initial={{ scale: 0 }}
      animate={{ scale: 1 }}
      transition={{ duration: 0.3, delay: 0.5 }}
      className="fixed bottom-6 right-6 z-50"
    >
      <Link to="/ai-assistant">
        <Button
          size="icon"
          variant="gradient"
          className="h-14 w-14 rounded-full shadow-glow animate-pulse-glow hover:scale-110"
        >
          <MessageCircle className="w-6 h-6" />
        </Button>
      </Link>
    </motion.div>
  );
};

export default AIFloatingButton;
