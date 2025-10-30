import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Activity, Bell, Brain, Calendar, Heart, Sparkles } from 'lucide-react';
import { motion } from 'framer-motion';

const Hero = () => {
  const features = [
    { icon: Bell, title: 'Smart Reminders', desc: 'Never miss your health routines' },
    { icon: Brain, title: 'AI Assistant', desc: 'Personalized health tips & support' },
    { icon: Calendar, title: 'Track Progress', desc: 'Visualize your wellness journey' },
    { icon: Heart, title: 'Stay Consistent', desc: 'Build lasting healthy habits' },
  ];

  return (
    <div className="min-h-screen">
      {/* Hero Section */}
      <section className="relative hero-gradient overflow-hidden">
        <div className="absolute inset-0 bg-[radial-gradient(circle_at_top_right,_hsl(var(--primary-glow)/0.15),transparent_50%)]" />
        <div className="absolute inset-0 bg-[radial-gradient(circle_at_bottom_left,_hsl(var(--accent)/0.1),transparent_50%)]" />
        
        <div className="container relative mx-auto px-4 py-20 md:py-32">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6 }}
            className="max-w-4xl mx-auto text-center"
          >
            <motion.div
              initial={{ scale: 0.9, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
              transition={{ duration: 0.5, delay: 0.2 }}
              className="inline-flex items-center gap-2 bg-primary/10 backdrop-blur px-4 py-2 rounded-full mb-6 border border-primary/20"
            >
              <Sparkles className="w-4 h-4 text-primary" />
              <span className="text-sm font-medium text-primary">Your Personal Health Companion</span>
            </motion.div>

            <h1 className="text-5xl md:text-7xl font-bold mb-6 leading-tight">
              Stay Healthy,
              <br />
              <span className="gradient-text">
                Stay Consistent
              </span>
            </h1>

            <p className="text-xl md:text-2xl text-muted-foreground mb-10 max-w-2xl mx-auto">
              Your daily health companion with smart reminders, AI assistance, and progress tracking
              to help you build lasting wellness habits.
            </p>

            <div className="flex flex-col sm:flex-row gap-4 justify-center items-center">
              <Link to="/auth?mode=signup">
                <Button variant="hero" size="xl">
                  <Activity className="w-5 h-5" />
                  Get Started Free
                </Button>
              </Link>
              <Link to="/auth?mode=login">
                <Button variant="outline" size="xl" className="backdrop-blur">
                  Login
                </Button>
              </Link>
            </div>

            <motion.div
              initial={{ opacity: 0, y: 30 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6, delay: 0.4 }}
              className="mt-20"
            >
              <div className="card-gradient rounded-3xl shadow-elevated p-8 md:p-12 border border-border/50 backdrop-blur">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                  {features.map((feature, index) => (
                    <motion.div
                      key={feature.title}
                      initial={{ opacity: 0, y: 20 }}
                      animate={{ opacity: 1, y: 0 }}
                      transition={{ duration: 0.4, delay: 0.6 + index * 0.1 }}
                      className="flex items-start gap-4 text-left"
                    >
                      <div className="wellness-gradient p-3 rounded-xl shadow-soft">
                        <feature.icon className="w-6 h-6 text-white" />
                      </div>
                      <div>
                        <h3 className="font-semibold text-lg mb-1">{feature.title}</h3>
                        <p className="text-muted-foreground">{feature.desc}</p>
                      </div>
                    </motion.div>
                  ))}
                </div>
              </div>
            </motion.div>
          </motion.div>
        </div>
      </section>

      {/* Stats Section */}
      <section className="py-20 bg-muted/30">
        <div className="container mx-auto px-4">
          <motion.div
            initial={{ opacity: 0 }}
            whileInView={{ opacity: 1 }}
            transition={{ duration: 0.6 }}
            viewport={{ once: true }}
            className="grid grid-cols-1 md:grid-cols-3 gap-8 max-w-4xl mx-auto"
          >
            {[
              { value: '100%', label: 'Free to Use' },
              { value: '5+', label: 'Health Categories' },
              { value: 'AI', label: 'Powered Assistant' },
            ].map((stat) => (
              <div key={stat.label} className="text-center">
                <div className="text-4xl md:text-5xl font-bold gradient-text mb-2">
                  {stat.value}
                </div>
                <div className="text-muted-foreground">{stat.label}</div>
              </div>
            ))}
          </motion.div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-20">
        <div className="container mx-auto px-4">
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6 }}
            viewport={{ once: true }}
            className="wellness-gradient rounded-3xl p-12 md:p-16 text-center shadow-glow"
          >
            <h2 className="text-3xl md:text-5xl font-bold text-white mb-4">
              Ready to Start Your Wellness Journey?
            </h2>
            <p className="text-xl text-white/90 mb-8 max-w-2xl mx-auto">
              Join thousands who are building healthier habits with HealthMate
            </p>
            <Link to="/auth?mode=signup">
              <Button
                size="xl"
                variant="secondary"
                className="shadow-elevated hover:shadow-glow hover:scale-105"
              >
                <Sparkles className="w-5 h-5" />
                Create Free Account
              </Button>
            </Link>
          </motion.div>
        </div>
      </section>
    </div>
  );
};

export default Hero;
