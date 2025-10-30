import { Activity, Heart } from 'lucide-react';

const Footer = () => {
  return (
    <footer className="bg-muted/30 border-t border-border mt-20">
      <div className="container mx-auto px-4 py-8">
        <div className="flex flex-col md:flex-row items-center justify-between gap-4">
          <div className="flex items-center gap-2">
            <Activity className="w-6 h-6 text-primary" />
            <span className="text-lg font-bold gradient-text">
              HealthMate
            </span>
          </div>

          <p className="text-sm text-muted-foreground flex items-center gap-2">
            Made with <Heart className="w-4 h-4 text-secondary fill-secondary" /> for your wellness
          </p>

          <p className="text-sm text-muted-foreground">
            © {new Date().getFullYear()} HealthMate. Stay healthy, stay consistent.
          </p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
