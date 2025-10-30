import { Link, useLocation } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { useAuth } from '@/context/AuthContext';
import { useSettings } from '@/context/SettingsContext';
import { Moon, Sun, Menu, X, Activity } from 'lucide-react';
import { useState } from 'react';

const Navbar = () => {
  const location = useLocation();
  const { isAuthenticated, logout } = useAuth();
  const { settings, toggleDarkMode } = useSettings();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  const navLinks = isAuthenticated
    ? [
        { to: '/dashboard', label: 'Dashboard' },
        { to: '/stats', label: 'Stats' },
        { to: '/ai-assistant', label: 'AI Assistant' },
        { to: '/settings', label: 'Settings' },
      ]
    : [];

  return (
    <nav className="sticky top-0 z-50 bg-background/80 backdrop-blur-lg border-b border-border shadow-soft">
      <div className="container mx-auto px-4 py-4">
        <div className="flex items-center justify-between">
          {/* Logo */}
          <Link to="/" className="flex items-center gap-2 group relative z-10">
            <Activity className="w-8 h-8 text-primary group-hover:scale-110 transition-smooth" />
            <span className="text-2xl font-bold gradient-text">
              HealthMate
            </span>
          </Link>

          {/* Desktop Navigation */}
          <div className="hidden md:flex items-center gap-6">
            {navLinks.map((link) => (
              <Link
                key={link.to}
                to={link.to}
                className={`text-sm font-medium transition-smooth hover:text-primary ${
                  location.pathname === link.to
                    ? 'text-primary'
                    : 'text-muted-foreground'
                }`}
              >
                {link.label}
              </Link>
            ))}

            {/* Dark Mode Toggle */}
            <Button
              variant="ghost"
              size="icon"
              onClick={toggleDarkMode}
              className="transition-smooth"
            >
              {settings.darkMode ? (
                <Sun className="w-5 h-5" />
              ) : (
                <Moon className="w-5 h-5" />
              )}
            </Button>

            {/* Auth Buttons */}
            {isAuthenticated ? (
              <Button onClick={logout} variant="outline">
                Logout
              </Button>
            ) : (
              <div className="flex gap-2">
                <Link to="/auth?mode=login">
                  <Button variant="ghost">Login</Button>
                </Link>
                <Link to="/auth?mode=signup">
                  <Button variant="gradient">Sign Up</Button>
                </Link>
              </div>
            )}
          </div>

          {/* Mobile Menu Button */}
          <Button
            variant="ghost"
            size="icon"
            className="md:hidden"
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          >
            {mobileMenuOpen ? <X /> : <Menu />}
          </Button>
        </div>

        {/* Mobile Menu */}
        {mobileMenuOpen && (
          <div className="md:hidden mt-4 pb-4 border-t border-border pt-4 animate-fade-in">
            <div className="flex flex-col gap-4">
              {navLinks.map((link) => (
                <Link
                  key={link.to}
                  to={link.to}
                  onClick={() => setMobileMenuOpen(false)}
                  className={`text-sm font-medium transition-smooth hover:text-primary ${
                    location.pathname === link.to
                      ? 'text-primary'
                      : 'text-muted-foreground'
                  }`}
                >
                  {link.label}
                </Link>
              ))}
              
              <div className="flex items-center gap-2">
                <Button
                  variant="ghost"
                  size="icon"
                  onClick={toggleDarkMode}
                  className="transition-smooth"
                >
                  {settings.darkMode ? <Sun className="w-5 h-5" /> : <Moon className="w-5 h-5" />}
                </Button>
                <span className="text-sm text-muted-foreground">
                  {settings.darkMode ? 'Dark' : 'Light'} Mode
                </span>
              </div>

              {isAuthenticated ? (
                <Button onClick={logout} variant="outline" className="w-full">
                  Logout
                </Button>
              ) : (
                <div className="flex flex-col gap-2">
                  <Link to="/auth?mode=login">
                    <Button variant="ghost" className="w-full">
                      Login
                    </Button>
                  </Link>
                  <Link to="/auth?mode=signup">
                    <Button variant="gradient" className="w-full">
                      Sign Up
                    </Button>
                  </Link>
                </div>
              )}
            </div>
          </div>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
